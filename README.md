# Drombler Commons

*Drombler Commons* is a collection of reusable libraries and frameworks. They ship with OSGi meta data but don't require an OSGi environment.

See the [documentation](https://www.drombler.org/drombler-commons) for the Javadoc and information about the provided Maven modules (available from Maven Central).

## Samples
There are a couple of samples for JavaFX based controls and utility classes: [drombler-commons-fx/drombler-commons-fx-samples](drombler-commons-fx/drombler-commons-fx-samples)
## Build the project from sources
```bash
mvn clean install
```
Please note that the develop branch (SNAPSHOT version) of the project might depend on SNAPSHOT versions of other projects.

If you don't want to build the dependent projects as well, please make sure to define a proxy in your [Maven Repository Manager](https://maven.apache.org/repository-management.html) to the following Maven Repository: https://oss.sonatype.org/content/repositories/snapshots/ and include it in your [single group](https://help.sonatype.com/repomanager3/formats/maven-repositories#MavenRepositories-ConfiguringApacheMaven).
