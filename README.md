[![GuardRails badge](https://api.guardrails.io/v2/badges/olivenbarcelon/um-java-ws.svg?token=5dd98b4b328796d3683b75456646fede05140d749a70cf328e56f71d7df47ee0&provider=github)](https://dashboard.guardrails.io/gh/olivenbarcelon/101105)
[![codecov](https://codecov.io/gh/olivenbarcelon/um-java-ws/branch/master/graph/badge.svg?token=T4PI6XYZ3X)](https://codecov.io/gh/olivenbarcelon/um-java-ws)
[![Java CI](https://github.com/olivenbarcelon/um-java-ws/actions/workflows/java-ci.yml/badge.svg?branch=master&event=push)](https://github.com/olivenbarcelon/um-java-ws/actions/workflows/java-ci.yml)
# um-java-ws
User Management System<br /><br />

System Requirements<br />
Install Spring CLI [with OSX Homebrew]:<br />
brew tap pivotal/tap<br />
brew install springboot<br /><br />

Setup Repository<br />
git clone https://olivenbarcelon@github.com/olivenbarcelon/um-java-ws.git<br /><br />

Setup Spring Application for Backend<br />
spring init --build=maven --java-version=8 --dependencies=webflux --packaging=jar --groupId=semicolon --artifactId=um-java-ws --package-name=semicolon.umjavaws -n=um-java-ws --description="User Management System" project --force<br /><br />

Run Spring Application<br />
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"<br /><br />

Run Maven Test<br />
mvn test -Dspring.profiles.active=dev<br /><br />
