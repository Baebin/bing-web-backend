package com.piebin.bingweb.global.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.piebin.bingweb.global.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String id;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Convert(converter = StringListConverter.class)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;
}
