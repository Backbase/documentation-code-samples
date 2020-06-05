# messaging-api

Fill out this file with some information about your OpenAPI spec.

To build this spec, use:
- mvn spring-boot:run

## Frontend Code Generation

### Generate and Build

Code is generated using [openapi-generator](https://openapi-generator.tech/).

```bash
mvn clean compile
cd target/generated-sources/openapi
npm install --@backbase:registry=https://repo.backbase.com/api/npm/npm-backbase/
npm run build
```

### Publish

After generating the `dist` of the generated sources can be published.

```
cd target/generated-sources/openapi/dist
npm publish
```

### Public API Report

Public API report is created using [api-extractor](https://www.npmjs.com/package/@microsoft/api-extractor).

*Note*: First run the code generation.

```bash
cd typescript-angular-api
npm install
npm run api:check
```
