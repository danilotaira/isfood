# defaul shell
SHELL = /bin/bash

# Rule "help"
.PHONY: help
.SILENT: help
help:
	echo "Use make [rule]"
	echo "Rules:"
	echo ""
	echo "build 		- build application and generate docker image"
	echo "run-db 		- run mysql database on docker"
	echo "run-app		- run application on docker"
	echo "stop-app	    - stop application"
	echo "stop-db		- stop database"
	echo "rm-app		- stop and delete application"
	echo "rm-db		    - stop and delete database"
	echo ""
	echo "k-setup		- init minikube machine"
	echo "k-deploy-db	- deploy mysql on cluster"
	echo "k-build-app	- build app and create docker image inside minikube"
	echo "k-deploy-app	- deploy app on cluster"
	echo ""
	echo "k-start		- start minikube machine"
	echo "k-all		    - do all the above k- steps"
	echo "k-stop		- stop minikube machine"
	echo "k-delete	    - stop and delete minikube machine"
	echo ""
	echo "check		    - check tools versions"
	echo "help		    - show this message"

run-dockers:
	mkdir -p ./docker/pgadmin
	chmod 777 -R ./docker/pgadmin
	docker-compose -f ./docker/docker-compose.yml up -d
	sleep 35s
	xdg-open http://localhost:15432

rm-dockers:
	sudo rm -rf ./docker/pgadmin
	sudo rm -rf ./docker/basedados
	docker-compose -f ./docker/docker-compose.yml down

stop-dockers:
	docker-compose -f ./docker/docker-compose.yml down


check:
	@echo Version 1.0.0.0
	@echo ------------------------------------------------------------------
	@echo "make version " && make --version
	@echo ------------------------------------------------------------------
	minikube version
	@echo ------------------------------------------------------------------
	echo "kubectl version" && kubectl version --short --client && echo