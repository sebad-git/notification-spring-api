CREATE TABLE notifications (
    id                  BIGSERIAL PRIMARY KEY,
    recipient           VARCHAR(50) NOT NULL,
    channel             VARCHAR(7) NOT NULL,
    subject             VARCHAR(100) NOT NULL,
    body                VARCHAR(255) NOT NULL,
    priority            VARCHAR(6) NOT NULL,
    metadata            JSONB DEFAULT '{}'::jsonb,
    status              VARCHAR(10) NOT NULL,
    created_at          TIMESTAMPTZ DEFAULT NOW() NOT NULL,
    updated_at          TIMESTAMPTZ DEFAULT NOW() NOT NULL
);