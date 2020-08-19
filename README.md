# java-ee-concurrency
# Build
mvn clean package && docker build -t com.mycompany/java-ee-concurrency .

# RUN

docker rm -f java-ee-concurrency || true && docker run -d -p 8080:8080 -p 4848:4848 --name java-ee-concurrency com.mycompany/java-ee-concurrency 
