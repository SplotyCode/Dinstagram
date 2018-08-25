var node = document.createElement("div");

window.onload = function() { 
    node.className = "loader";
    node.innerHTML = 
        "   <div></div>" + 
        "   <div></div>" + 
        "   <div></div>" + 
        "   <div></div>" + 
        "   <div></div>" + 
        "   <div></div>";
    document.body.appendChild(node);
}

function showLoader() {
    node.style.display = "flex";
    console.log('loader shown');
}
function hideLoader() {
    node.style.display = "none";
    console.log('loader hidden');
}