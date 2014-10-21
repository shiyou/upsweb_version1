<!DOCTYPE html> 
<html lang="en"> 
<head> 
<meta charset="utf-8"> 
<title>Bootstrap Popover Example</title> 
<meta name="description" content="This is an example to create Popover with Bootstrap.">
<link rel="stylesheet" href="./common/bootstrap/css/bootstrap.css" type="text/css"></link>
</head>
<body>
<div class="container">
<h2>Example of creating Popover with Bootstrap</h2>
<div class="well">
<a href="#" id="example" class="btn btn-success" rel="popover" data-content="It's so simple to create a tooltop for my website!" data-original-title="Bootstrap Popover">hover for popover</a>
</div>
</div>
<script type="text/javascript" src="./common/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="./common/bootstrap/js/tooltip.js"></script>
<script type="text/javascript" src="./common/bootstrap/js/popover.js"></script>


<script>
$(function ()
{ $("#example").popover();
});
</script>
</body>
</html>

