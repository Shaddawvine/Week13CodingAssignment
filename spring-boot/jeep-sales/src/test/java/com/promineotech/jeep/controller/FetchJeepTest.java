
package com.promineotech.jeep.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import lombok.Getter;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("Test")
@Sql(scripts = {
    "classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
    "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
    config = @SqlConfig(encoding = "utf-8"))

class FetchJeepTest {
  @LocalServerPort
  private int serverPort;
  @Autowired
  @Getter
  private TestRestTemplate restTemplate;
  @Test
  void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
   JeepModel model = JeepModel.WRANGLER;
   String trim = "Sport";
   String uri = String.format(
       "http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);
   
   ResponseEntity<Jeep> response = getRestTemplate().getForEntity(uri, Jeep.class); 
   
   assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); 
   
  }

}
