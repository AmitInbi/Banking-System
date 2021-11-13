from flask import Flask, render_template, request, redirect, url_for

from py4j.java_gateway import JavaGateway

app = Flask(__name__)
app.config['SEND_FILE_MAX_AGE_DEFAULT'] = 0  # stop app caching


@app.route("/", methods=["GET", "POST"])
def homePage():
    if not request.args.get("LoggedIn"):
        return redirect(url_for("LoginPage"))
    else:
        user = request.args.get("username")
        return render_template("home.html")


@app.route("/Login_page", methods=["GET", "POST"])
def LoginPage():
    if request.method == "POST":
        username = request.form['username']
        password = request.form['password']
        # TODO: verify username and password by checking hash values in database
        return redirect(url_for("homePage", LoggedIn=True, username=username))
    else:
        return render_template("login_page.html")


@app.route("/Deposit", methods=["GET", "POST"])
def Deposit():
    if request.method == "POST":
        gateway = JavaGateway()
        api = gateway.entry_point.getAPI()

        return
    else:
        return render_template("Deposit.html")


if __name__ == '__main__':
    print("Initializing app")
    app.run(debug=True)
