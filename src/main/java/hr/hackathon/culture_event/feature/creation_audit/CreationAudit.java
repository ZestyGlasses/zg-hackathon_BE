package hr.hackathon.culture_event.feature.creation_audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CreationAudit {

  @CreatedDate private LocalDate dateCreated;

  @LastModifiedDate private LocalDate dateModified;

  @CreatedBy private String userCreated;

  @LastModifiedBy private String userModified;
}
