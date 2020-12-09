<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ attribute name="buttonKey" required="true" %>
<%@ attribute name="buttonAction" rtexprvalue="true" %>
<%@ attribute name="subClass" %>

<button class="btn ${empty subClass ? 'btn-outline-primary' : subClass}"
        type="submit"
        <c:if test="${not empty buttonAction}" >formaction="${buttonAction}"</c:if> >
    <fmt:message key="${buttonKey}" />
</button>