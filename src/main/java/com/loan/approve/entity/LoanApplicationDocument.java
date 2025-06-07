package com.loan.approve.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "loan_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoanApplicationDocument {

    @Id
    private String id;
    private Long userId;
    private Double amount;
    private String status;
    private String applicantName;
    private String applicationDate;

}