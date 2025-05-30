-- Creating the Subscription table
--MySQL
--CREATE TABLE IF NOT EXISTS "subscription" (
CREATE TABLE IF NOT EXISTS "SUBSCRIPTION" (
    "SUBSCRIPTION_ID" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "LOAD_TYPE" VARCHAR(255) NOT NULL,
    "JOB_DEFINITION" VARCHAR(255) NOT NULL,
    "JOB_QUEUE" VARCHAR(255) NOT NULL
);

-- Creating the SubscriptionRun table
--CREATE TABLE IF NOT EXISTS "subscription_run" (
CREATE TABLE IF NOT EXISTS "SUBSCRIPTION_RUN" (
    "SUBSCRIPTION_RUN_ID" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "SUBSCRIPTION_ID" BIGINT NOT NULL,
    "SUBSCRIPTION_START_DATETIME" DATETIME NOT NULL,
    "SUBSCRIPTION_END_DATETIME" DATETIME NOT NULL,
    "RUN_STATUS" VARCHAR(50) NOT NULL,
    FOREIGN KEY ("SUBSCRIPTION_ID") REFERENCES "SUBSCRIPTION"("SUBSCRIPTION_ID")
);

