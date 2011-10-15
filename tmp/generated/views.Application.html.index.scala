
                    package views.Application.html

                    import play.templates._
                    import play.templates.TemplateMagic._
                    import views.html._

                    object index extends BaseScalaTemplate[Html,Format[Html]](HtmlFormat) {

                        def apply/*1.2*/(title:String):Html = {
                            try {
                                _display_ {

format.raw/*1.16*/("""

""")+_display_(/*3.2*/main(title)/*3.13*/ {format.raw/*3.15*/("""
    
    <h1>TESTing</h1>
    
""")})}
                            } catch {
                                case e:TemplateExecutionError => throw e
                                case e => throw Reporter.toHumanException(e)
                            }
                        }

                    }

                
                /*
                    -- GENERATED --
                    DATE: Thu Oct 13 19:09:01 EDT 2011
                    SOURCE: /app/views/Application/index.scala.html
                    HASH: 472f8f0b75b9b72539fe1474f50c235e0ca44b37
                    MATRIX: 329->1|450->15|478->18|497->29|517->31
                    LINES: 10->1|14->1|16->3|16->3|16->3
                    -- GENERATED --
                */
            
