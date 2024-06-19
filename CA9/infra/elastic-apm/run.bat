@ECHO OFF
SETLOCAL EnableExtensions DisableDelayedExpansion

SET "miz_ver=1.0-SNAPSHOT"
SET "miz_jar=..\..\back-end\target\mizdooni-%miz_ver%.jar"

SET "apm_ver=1.50.0"
SET "apm_jar=elastic-apm-agent-%apm_ver%.jar"
SET "apm_cfg=elasticapm.properties"

java -javaagent:%apm_jar% -Delastic.apm.config_file=%apm_cfg% -jar %miz_jar%
