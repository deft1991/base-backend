CREATE TABLE IF NOT EXISTS sms
(
    id                         BIGINT                                      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    externalUserId             VARCHAR(36)                                 NOT NULL,
    phoneNumber                double                                      NOT NULL,
    smsType                    VARCHAR(20)                                 NOT NULL,
    verificationCode           VARCHAR(20)                                 NOT NULL,
    responceSmsId              BIGINT                                      NOT NULL,
    expirationDate             TIMESTAMP                                   DEFAULT CURRENT_TIMESTAMP,
    createdDate                TIMESTAMP                                   DEFAULT CURRENT_TIMESTAMP,
    updatedDate                TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB;
