<!DOCTYPE html>
<html>
<head>
    <title>Login/Signup</title>
    <link rel="stylesheet" type="text/css" href="LoginRegistrazione.css">
</head>
<body>
<div class="wrapper">
    <div class="title-text">
        <div class="title login">Login Form</div>
        <div class="title signup">Signup Form</div>
    </div>
    <div class="form-container">
        <div id="registrationMessage" style="display: none; color: red; text-align: center; margin-top: 10px;">Devi prima registrarti prima di fare il login.</div>
        <div class="slide-controls">
            <input type="radio" name="slide" id="login" checked>
            <input type="radio" name="slide" id="signup">
            <label for="login" class="slide login">Login</label>
            <label for="signup" class="slide signup">Signup</label>
            <div class="slider-tab"></div>
        </div>
        <div class="form-inner">
            <!-- Form di login -->
            <form action="LoginServlet" method="post" class="login">
                <div class="field">
                    <input type="text" name="username" placeholder="Username" required>
                </div>
                <div class="field">
                    <input type="password" name="password" placeholder="Password" required>
                </div>
                <div class="field btn">
                    <div class="btn-layer"></div>
                    <input type="submit" value="Login">
                </div>
                <div class="signup-link">Not a member? <a href="">Signup now</a></div>
            </form>
            <!-- Form di registrazione -->
            <form action="RegistrazioneServlet" method="post" class="signup">
                <div class="field">
                    <input type="email" name="email" placeholder="Email" required>
                </div>
                <div class="field">
                    <input type="text" name="username" placeholder="Username" required>
                </div>
                <div class="field">
                    <input type="password" name="password" placeholder="Password" required>
                </div>
                <div class="field btn">
                    <div class="btn-layer"></div>
                    <input type="submit" value="Signup">
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    const loginText = document.querySelector(".title-text .login");
    const loginForm = document.querySelector("form.login");
    const loginBtn = document.querySelector("label.login");
    const signupBtn = document.querySelector("label.signup");
    const signupLink = document.querySelector("form .signup-link a");
    signupBtn.onclick = (()=>{
        loginForm.style.marginLeft = "-50%";
        loginText.style.marginLeft = "-50%";
    });
    loginBtn.onclick = (()=>{
        loginForm.style.marginLeft = "0%";
        loginText.style.marginLeft = "0%";
    });
    signupLink.onclick = (()=>{
        signupBtn.click();
        return false;
    });

</script>

</body>
</html>
