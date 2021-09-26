1) Enter your api key information in the file application.properties and start the project.

2) API-REST
=========================================================
GET /code/{symbol}

Parameter:
    {symbol}  type string

response :
    if show.gif.to.html = false
       returns: String url-gif
	
	if show.gif.to.html = true (default)
	    returns: the page with the display url-gif

Example:
	http://localhost:8099/code/AOA
=========================================================
3) To run in docker :
   1. Build the project
   2. To assemble in docker, use the command : docker build -t v1 .
   3. To start the container docker : docker run -p 8099:8099 v1

4) If you made the changes,  try step 3) again.

Info : Symbol code can be viewed here  https://docs.openexchangerates.org/docs/supported-currencies
