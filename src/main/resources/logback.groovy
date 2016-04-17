/**
 * Created by Administrator on 2016/4/14.
 */
def USER_HOME = System.getProperty("user.home");
println "USER_HOME=${USER_HOME}"

appender("FILE", FileAppender) {
    println "Setting [file] property to [${USER_HOME}/springStudy.log]"
    file = "${USER_HOME}/springStudy.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
    }
}
root(INFO, ["FILE"])