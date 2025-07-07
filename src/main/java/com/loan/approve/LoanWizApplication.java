package com.loan.approve;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@OpenAPIDefinition(
		info = @Info(
				title = "Loan Approval API",
				version = "1.0",
				description = "API for loan application processing"
		)
)
@SpringBootApplication
@EntityListeners(AuditingEntityListener.class)
public class LoanWizApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanWizApplication.class, args);
	}

}
//http://localhost:8080/swagger-ui/
