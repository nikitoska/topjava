<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.12/css/jquery.dataTables.min.css">
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><fmt:message key="meals.title"/></h3>
    <div class="jumbotron">
        <div class="container">

    <form class="form-horizontal" method="post" id="filterForm">


            <div class="form-group">
                <label  class="col-sm-2 control-label">From Date</label>
                <div class="col-sm-2">
            <input type="date" name="startDate" id = "startDate" class="form-control">
                </div>
                </div>



        <div class="form-group">
            <label  class="col-sm-2 control-label">To Date</label>
            <div class="col-sm-2">
            <input type="date" name="endDate" id="endDate" class="form-control">
                </div>
            </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">From Time</label>
            <div class="col-sm-2">
            <input type="time" name="startTime" id="startTime" class="form-control">
        </div>
            </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">To Time</label>
            <div class="col-sm-2">
            <input type="time" name="endTime" id="endTime" class="form-control">
                </div>
            </div>
        <div class="col-sm-offset-2 col-sm-10">
        <button  type="submit" class = "btn btn-sm btn-info" id="filter"><fmt:message key="meals.filter"/></button>
            </div>

    </form>


            <div class="shadow">

                <a class = "btn btn-sm btn-info" id="add"  ><fmt:message key="meals.add"/></a>

               <div class="view-box">

                    <table class="table table-striped display" id="datatable">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}" id="${meal.id}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a class="btn btn-xs btn-primary edit" >Update</a></td>
                <td><a class="btn btn-xs btn-danger delete" >Delete</a></td>
            </tr>
        </c:forEach>
    </table>
                   </div>
                </div>
            </div>
        </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3">DateTime</label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" size=40 name="description" placeholder="description">
                        </div>
                    </div>

                    <div class="form-group">
                    <label for="calories" class="control-label col-xs-3">Calories</label>

                    <div class="col-xs-9">
                        <input type="number" class="form-control" id="calories" name="calories" placeholder="calories">
                    </div>
                        </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">
    var ajaxUrl = 'ajax/profile/meals/';

    var datatableApi;
    function updateTable() {
        $.ajax({
            type: "POST",
            url: ajaxUrl + 'filter',
            data: $('#filterForm').serialize(),
            success: function (data) {
                updateTableByData(data);
            }
        });
        return false;
    }
    // $(document).ready(function () {
    $(function () {
        datatableApi = $('#datatable').DataTable({
            "paging": false,
            "info": false,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },

                {
                    "sDefaultContent": "Update",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "Delete",
                    "bSortable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        });
        makeEditable();
    });
</script>
</html>
