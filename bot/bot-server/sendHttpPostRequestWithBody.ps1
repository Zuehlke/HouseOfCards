#In order to smoke test the example noker bot this script sends a HTTP post request with a JSON body to the define URI. 
$registerinfourl = "http://localhost:8081/register_info"
$matchstartedurl = "http://localhost:8081/match_started"
$body = "{`"infoMessage`": `"CONFIRMATION`", `"playerName`": `"hansdampf`", `"token`":`"8081`"}"
$contentType = "application/json"            

Invoke-RestMethod -Method Post -Uri $registerinfourl -Body $body -ContentType $contentType

Invoke-RestMethod -Method Post -Uri $matchstartedurl -Body $body -ContentType $contentType