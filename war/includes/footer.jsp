</div>
</body>
		<div id="footer">
			<%@ page import="java.util.Calendar" %>
			<%  
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
			%>
			<p>Copyright &copy;<%= year %>, Adrian Lesniak. All rights reserved.</p>
		</div>
	</div>
</html>