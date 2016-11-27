#In order to smoke test the example noker bot this script sends a HTTP post request with a JSON body to the define URI. 
$url = "http://localhost:8081"
$body = "{`"registrationResult`": `"CONFIRMATION`"}"
$contentType = "application/json"            

Invoke-RestMethod -Method Post -Uri $url -Body $body -ContentType $contentType