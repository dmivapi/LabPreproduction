<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ tag import="com.epam.dmivapi.ContextParam" %>
<%@ tag import="com.epam.dmivapi.controller.Command" %>

<c:set var="aclasses" value="class=\"list-group-item list-group-item-action bg-light lsa-panel-link\"" />

<sec:authorize access="hasRole('ADMIN')">
    <a href="${Command.LIST_USERS_READERS_FOR_ADMIN.systemName}" ${aclasses}><fmt:message key="left_side_actions_panel_tag.usersBlocking"/></a>
    <a href="${Command.ENTER_USER_INFO.systemName}" ${aclasses}><fmt:message key="left_side_actions_panel_tag.addLibrarian"/></a>
    <a href="${Command.LIST_USERS_LIBRARIANS.systemName}" ${aclasses}><fmt:message key="left_side_actions_panel_tag.viewRemoveLibrarians"/></a>
    <a href="${Command.LIST_BOOKS.systemName}" ${aclasses}><fmt:message key="left_side_actions_panel_tag.books"/></a>
</sec:authorize>

<sec:authorize access="hasRole('LIBRARIAN')">
    <a href="${Command.LIST_USERS_READERS_FOR_LIBRARIAN.systemName}" ${aclasses}><fmt:message key="left_side_actions_panel_tag.readers"/></a>
    <a href="${Command.LIST_LOANS_OF_ALL.systemName}" ${aclasses}><fmt:message key="left_side_actions_panel_tag.bookLoans"/></a>
</sec:authorize>

<sec:authorize access="hasRole('READER')">
    <a href="${Command.LIST_BOOKS.systemName}" ${aclasses}><fmt:message key="left_side_actions_panel_tag.books"/></a>
    <a href="${Command.LIST_LOANS_OF_SELF.systemName}" ${aclasses}><fmt:message key="left_side_actions_panel_tag.myLoans"/></a>
</sec:authorize>

<sec:authorize access="!isAuthenticated()">
    <a href="${Command.LIST_BOOKS.systemName}"  ${aclasses} ><fmt:message key="left_side_actions_panel_tag.books"/></a>
</sec:authorize>

<script>
    $(document).ready(function(){
        $('.lsa-panel-link').click(function(event)
        {
            event.preventDefault();
            $('#${ContextParam.MAIN_PAGE_FORM}').attr('action', $(this).attr('href'));
            $('#${ContextParam.PGN_CURRENT_PAGE}').val(1);
            $('#${ContextParam.MAIN_PAGE_FORM}').submit();
        });
    });
</script>