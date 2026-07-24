package org.uy.sdm.notificator.modules.notification.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashMap;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = DataTables.NOTIFICATIONS)
public class Notification implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 50)
	private String recipient;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Channel channel;

	@Column(nullable = false, length = 100)
	private String subject;

	@Column(nullable = false)
	private String body;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 6)
	private Priority priority;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private HashMap<String,Object> metadata;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Status status;

	@Column(nullable = false, columnDefinition = "created_at")
	private OffsetDateTime createdAt;

	@Column(nullable = false, columnDefinition = "updated_at")
	private OffsetDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		OffsetDateTime now = OffsetDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = OffsetDateTime.now();
	}

}
