import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{MustMatchers, WordSpec}

class AkkaCirceNullableJsonSpec
    extends WordSpec
    with MustMatchers
    with ScalaFutures
    with ScalatestRouteTest
    with FailFastCirceSupport {

  val nullString = "null"

  type TargetType = Option[List[String]]

  "Circe parse" must {
    "return a JsonNull value" in {
      parser.parse(nullString) mustBe Right(Json.Null)
    }
  }

  "Circe decode" must {
    "return a None value" in {
      parser.decode[TargetType](nullString) mustBe Right(None)
    }
  }

  "Circe decodeJson" must {
    "return a None value" in {
      val jsonNull = Json.Null

      Decoder[TargetType].decodeJson(jsonNull).fold(throw _, identity) mustBe None
    }
  }

  "Unmarshal" when {
    val entity   = HttpEntity.Strict(ContentTypes.`application/json`, ByteString(nullString))
    val response = HttpResponse(entity = entity)

    "using the safe api" when {
      "unmarshalling a HttpEntity" must {
        "return a None value" in {
          Unmarshal(entity).to[Either[io.circe.Error, TargetType]].futureValue mustBe Right(None)
        }
      }

      "unmarshalling a HttpResponse" must {
        "return a None value" in {
          Unmarshal(response).to[Either[io.circe.Error, TargetType]].futureValue mustBe Right(None)
        }
      }
    }

    "using the unsafe api" when {
      "unmarshalling a HttpEntity" must {
        "return a None value" in {
          Unmarshal(entity).to[TargetType].futureValue mustBe None
        }
      }

      "unmarshalling a HttpResponse" must {
        "return a None value" in pendingUntilFixed {
          // This doesn't work as expected since `GenericUnmarshallers` lift everything that expects an Option
          // automatically as a means of silencing exceptions and the Option is no longer propagated to circe.
          // Circe then correctly complains that JNull cannot be decoded as non-nullable by default.

          Unmarshal(response).to[TargetType].futureValue mustBe None
        }
      }
    }

    // The following only side-steps the issue, but it's a very nice solution if you expect a nullable json array
    "using the unsafe api with a custom decoder as alternative/workaround" when {
      "unmarshalling a HttpResponse" must {
        "return an empty List for an nulled expected json array" in {
          // Custom decoder which decodes json arrays as lists but falls back to empty List if the json array is null
          implicit def decodeNullableList[A: Decoder]: Decoder[List[A]] =
            Decoder.decodeList[A] or Decoder.decodeOption[List[A]].map(_.getOrElse(List.empty))

          Unmarshal(response).to[List[String]].futureValue mustBe List.empty
        }
      }
    }
  }
}
