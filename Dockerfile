FROM airhacks/glassfish
COPY ./target/java-ee-concurrency.war ${DEPLOYMENT_DIR}
