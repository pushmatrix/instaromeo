
                    package views.html

                    import play.templates._
                    import play.templates.TemplateMagic._
                    import views.html._

                    object main extends BaseScalaTemplate[Html,Format[Html]](HtmlFormat) {

                        def apply/*1.2*/(title:String = "")(body: => Html):Html = {
                            try {
                                _display_ {

format.raw/*1.36*/("""

<!DOCTYPE html>
<html>
    <head>
        <title>""")+_display_(/*6.17*/title)+format.raw/*6.22*/("""</title>
   		<link rel="stylesheet" href="http://twitter.github.com/bootstrap/1.3.0/bootstrap.min.css">
		<link rel="stylesheet" href="""")+_display_(/*8.33*/asset("public/stylesheets/main.css"))+format.raw/*8.69*/("""">
		<link rel="shortcut icon" type="image/png" href="""")+_display_(/*9.53*/asset("public/images/favicon.png"))+format.raw/*9.87*/("""">
        <script src="""")+_display_(/*10.23*/asset("public/javascripts/jquery-1.5.2.min.js"))+format.raw/*10.70*/("""" type="text/javascript"></script>
    </head>
    <body>
		<div class="topbar">
			<div class="fill">
				<div class="container">
					<img class="nav-logo" src="""")+_display_(/*16.34*/asset("public/images/favicon.png"))+format.raw/*16.68*/(""""/>
					<a class="brand" href="#">instaRomeo</a>
					<ul class="nav">
						<li class="active">
							<a href="#">Home</a>
						</li>
						<li>
							<a href="#">Tab1</a>
						</li>
						<li>
							<a href="#">Tab2</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="hero-unit">
        		""")+_display_(/*34.12*/body)+format.raw/*34.16*/("""
			</div>
		</div>
    </body>
</html>
""")}
                            } catch {
                                case e:TemplateExecutionError => throw e
                                case e => throw Reporter.toHumanException(e)
                            }
                        }

                    }

                
                /*
                    -- GENERATED --
                    DATE: Thu Oct 13 19:41:27 EDT 2011
                    SOURCE: /app/views/main.scala.html
                    HASH: d791e61d0878a249354bcaf9649f6e7d046a44b0
                    MATRIX: 316->1|457->35|535->87|560->92|723->229|779->265|860->320|914->354|966->379|1034->426|1225->590|1280->624|1652->969|1677->973
                    LINES: 10->1|14->1|19->6|19->6|21->8|21->8|22->9|22->9|23->10|23->10|29->16|29->16|47->34|47->34
                    -- GENERATED --
                */
            
