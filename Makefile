LOCAL_DC_FILE := $(CURDIR)/docker-compose.yml

up:
	@docker-compose -f $(LOCAL_DC_FILE) build && \
	docker-compose -f $(LOCAL_DC_FILE) up -d

down:
	@docker-compose -f $(LOCAL_DC_FILE) down

health:
	@sh ./schema/test/health.sh

default: up