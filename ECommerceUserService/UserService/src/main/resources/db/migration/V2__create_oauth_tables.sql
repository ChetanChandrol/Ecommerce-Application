CREATE TABLE client (
                        id VARCHAR(100) NOT NULL,
                        client_id VARCHAR(100) NOT NULL,
                        client_id_issued_at TIMESTAMP NULL,
                        client_secret VARCHAR(200) NULL,
                        client_secret_expires_at TIMESTAMP NULL,
                        client_name VARCHAR(200) NOT NULL,
                        client_authentication_methods TEXT,
                        authorization_grant_types TEXT,
                        redirect_uris TEXT,
                        post_logout_redirect_uris TEXT,
                        scopes TEXT,
                        client_settings TEXT,
                        token_settings TEXT,
                        PRIMARY KEY (id),
                        UNIQUE KEY uk_client_client_id (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE authorization (
                               id VARCHAR(100) NOT NULL,
                               registered_client_id VARCHAR(100) NOT NULL,
                               principal_name VARCHAR(200) NOT NULL,
                               authorization_grant_type VARCHAR(100) NOT NULL,
                               authorized_scopes TEXT,
                               attributes TEXT,
                               state VARCHAR(500),

                               authorization_code_value TEXT,
                               authorization_code_issued_at TIMESTAMP NULL,
                               authorization_code_expires_at TIMESTAMP NULL,
                               authorization_code_metadata TEXT,

                               access_token_value TEXT,
                               access_token_issued_at TIMESTAMP NULL,
                               access_token_expires_at TIMESTAMP NULL,
                               access_token_metadata TEXT,
                               access_token_type VARCHAR(100),
                               access_token_scopes TEXT,

                               refresh_token_value TEXT,
                               refresh_token_issued_at TIMESTAMP NULL,
                               refresh_token_expires_at TIMESTAMP NULL,
                               refresh_token_metadata TEXT,

                               oidc_id_token_value TEXT,
                               oidc_id_token_issued_at TIMESTAMP NULL,
                               oidc_id_token_expires_at TIMESTAMP NULL,
                               oidc_id_token_metadata TEXT,
                               oidc_id_token_claims TEXT,

                               user_code_value TEXT,
                               user_code_issued_at TIMESTAMP NULL,
                               user_code_expires_at TIMESTAMP NULL,
                               user_code_metadata TEXT,

                               device_code_value TEXT,
                               device_code_issued_at TIMESTAMP NULL,
                               device_code_expires_at TIMESTAMP NULL,
                               device_code_metadata TEXT,

                               PRIMARY KEY (id),
                               INDEX idx_authorization_registered_client_id (registered_client_id),
                               INDEX idx_authorization_principal_name (principal_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE authorization_consent (
                                       registered_client_id VARCHAR(100) NOT NULL,
                                       principal_name VARCHAR(200) NOT NULL,
                                       authorities TEXT,
                                       PRIMARY KEY (registered_client_id, principal_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;