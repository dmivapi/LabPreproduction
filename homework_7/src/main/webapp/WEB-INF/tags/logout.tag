<%@ tag pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>
<%@ tag import="com.epam.dmivapi.controller.Command" %>

<form action="${Command.LOGOUT.systemName}" method="POST" id="logoutForm">
    <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <sec:authorize access="isAuthenticated()">
                <sec:authentication property="principal.firstName"/>
                <sec:authentication property="principal.lastName"/>
                (<sec:authentication property="principal.userRole"/>)
            </sec:authorize>
        </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                <li id="logout" class="dropdown-item"><fmt:message key="actions_list_general_tag.logout"/></li>
        </ul>
    </li>
</form>

<script>
    $(document).ready(function(){
        $('#logout').click(function()
        {
            $('#logoutForm').submit();
        });
    });
</script>