
<select id="createTable" statementType="STATEMENT">
	<![CDATA[ CREATE TABLE ${value} (id BIGINT(20) NOT NULL AUTO_INCREMENT,accessTime BIGINT(20) NOT NULL DEFAULT '0',formatTime VARCHAR(20) NOT NULL DEFAULT '',number VARCHAR(40) NOT NULL DEFAULT '',province VARCHAR(40) NULL DEFAULT '',action VARCHAR(200) NOT NULL DEFAULT '',actionName VARCHAR(40) NOT NULL DEFAULT '',params VARCHAR(1000) NOT NULL DEFAULT '',`type` VARCHAR(20) NOT NULL DEFAULT '',objectId VARCHAR(20) NOT NULL DEFAULT '',title VARCHAR(100) NOT NULL DEFAULT '',channelId VARCHAR(60) NOT NULL DEFAULT '',channelName VARCHAR(20) NOT NULL DEFAULT '',breedId VARCHAR(20) NOT NULL DEFAULT '', cityId VARCHAR(20) NOT NULL DEFAULT '', PRIMARY KEY (`id`)) ]]>
</select>