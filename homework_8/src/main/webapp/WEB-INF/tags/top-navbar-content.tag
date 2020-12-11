<%@ tag pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>

<div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
        <h:lang-selector />
        <sec:authorize access="!isAuthenticated()">
            <li class="nav-item active">
                <h:login_register />
            </li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <li class="nav-item active">
                <h:logout />
            </li>
        </sec:authorize>
    </ul>
</div>