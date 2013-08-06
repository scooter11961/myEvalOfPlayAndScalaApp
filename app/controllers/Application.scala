package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._


object Application extends Controller {
  
  def index = Action {
//    Ok(views.html.index("Your new application is ready."))
//    Ok(views.html.index("Hello World"))
    Redirect(routes.Application.tasks)
  }

  import models.Task

  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      }
    )
  }


  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def deleteTask(id: Long) = Action {
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }

}