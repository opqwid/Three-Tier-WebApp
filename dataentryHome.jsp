/*  Name: Vincent Qiu
    Course: CNT 4714 – Summer 2022 – Project Three 
    Assignment title:  A Three-Tier Distributed Web-Based Application 
    Date:  August 4, 2022 
*/ 
<!DOCTYPE html>
<html>
	<head>
		<title>Summer 2022 Project 3 Enterprise Database System</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<style>
			h1 {
				font-family: Arial, sans-serif;
                font-size: 34px;
				color: #ff0000;
				text-align: default;
			}
            h2 {
				font-family: Arial, sans-serif;
                font-size: 30px;
				color: #00faff;
				text-align: default;
			}
			p {
				font-family: Arial, sans-serif;
				font-size: 14px;
				text-align: default;
				color: #ffffff;
			}
            .clearbutton {
				font-family: Arial, sans-serif;
				font-size: 20px;
				text-align: default;
				color: #ff0000;
                background: #767575;
			}
            .submitbutton {
				font-family: Arial, sans-serif;
				font-size: 20px;
				text-align: default;
				color: #07f61f;
                background: #767575;
			}
            mark.red {
                 color:#ff0000;
                 background: none;
            }
            .queryinput {
                 color: #ffffff;
                 background: #0015ff;
                 font-family: Arial, sans-serif;
                 font-size: 20px;
                 border:solid 3px yellow;
                 
            }
            .queryheader {
                 color: #ffffff;
                 background: #000000;
                 font-family: Arial, sans-serif;
                 font-size: 20px;
                 text-align: center;
                 border:solid 3px yellow;
            }

		</style>
	</head>
	<body style="background-color: black;">
        <center>
		<h1>Welcome to the Summer 2022 Project 3 Enterprise Database System</h1>
        <h2>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</h2>
        <hr>
		<p>You are connected to the Project 3 Enterprise System database as a <mark class = "red">data-entry-level</mark> user.</p>
        <p>Please enter the data values in the form below to add a new record to the shipments table.</p>
        <hr>
        <br>
        <br>
        <br>
        <form action="DataEntryUserApp">
            <textarea readonly rows="1" cols="20" class=queryheader>snum</textarea>
            <textarea readonly rows="1" cols="20" class=queryheader>pnum</textarea>
            <textarea readonly rows="1" cols="20" class=queryheader>jnum</textarea>
            <textarea readonly rows="1" cols="20" class=queryheader>quantity</textarea>
            <br>
            <textarea id="snum" query="snum" rows="1" cols="20" class=queryinput></textarea>
            <textarea id="pnum" query="pnum" rows="1" cols="20" class=queryinput></textarea>
            <textarea id="jnum" query="jnum" rows="1" cols="20" class=queryinput></textarea>
            <textarea id="quantity" query="quantity" rows="1" cols="20" class=queryinput></textarea>
            <br>
            <br>
            <br>
            <input type="submit" value="Enter Record Into Database" class="submitbutton"> 
            <input type="button" value="Clear Results" class="clearbutton">
          </form>

        <br>
        <hr>
        <br>
        <p><b>Database Results:</b></p>
        <br>
        <br>
        <br>
        <br>
        </center>
	</body>
</html>