test:
	gradle test

start:
	gradle bootRun

make api-doc:
	gradle clean generateOpenApiDocs

build:
	./gradlew clean build

install:
	./gradlew installDist

start-dist:
	./build/install/service-layer/bin/service-layer

.PHONY: build
