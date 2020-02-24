import java.util.concurrent.TimeUnit.SECONDS

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.FiniteDuration

package object model {
  val httpProtocol = http
    .baseUrl("http://localhost:8080/api/mails")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
}

class CreateEmailSimulation extends Simulation {

  val scn = scenario("BasicSimulation")
    .repeat(10, "n") {
      exec(http("")
        .post("/")
        .body(RawFileBody("post_mail.json"))
        .check(status.is(200))
      )
    }

  setUp(
    scn.inject(rampUsers(1000).during(FiniteDuration(5, SECONDS)))
  ).protocols(model.httpProtocol)
}

class GetSimulation extends Simulation {

  object Create {
    val email = exec(http("")
      .post("/")
      .body(RawFileBody("post_mail.json")))
  }

  object Get {
    val email =
      exec(http("")
        .get("/1")
        .check(status.is(200))
      )
  }

  val createEmailScn = scenario("create").exec(Create.email)
  val getEmailScn = scenario("GetSimulation").exec(Get.email)

  setUp(
    createEmailScn.inject(atOnceUsers(1)),
    getEmailScn.inject(constantConcurrentUsers(500).during(FiniteDuration(30, SECONDS)))
  ).protocols(model.httpProtocol)

}