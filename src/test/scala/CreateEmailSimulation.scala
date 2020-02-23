import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CreateEmailSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080/api/mails")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("BasicSimulation")
    .repeat(10, "n") {
      exec(http("")
        .post("/")
        .body(RawFileBody("post_mail.json"))
        .check(status.is(200))
      )

    }

  setUp(
    scn.inject(atOnceUsers(1000))
  ).protocols(httpProtocol)
}