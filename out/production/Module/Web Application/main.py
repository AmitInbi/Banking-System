from flask import Flask, render_template, request, redirect, url_for, session
from os import urandom

from py4j.java_gateway import JavaGateway

app = Flask(__name__)
# SESSION_TYPE = 'redis'
app.config['SECRET_KEY'] = urandom(16).hex()
app.config['SEND_FILE_MAX_AGE_DEFAULT'] = 0  # stop app caching


@app.route("/", methods=["GET", "POST"])
def homePage():
    if not bool(session.get("LoggedIn")):
        return redirect(url_for("LoginPage"))
    else:
        user_id = session.get("user_id")
        user_name = session.get("user_name")
        return render_template("home.html", user_name=user_name)


@app.route("/Login_page", methods=["GET", "POST"])
def LoginPage():
    if request.method == "POST":
        username = request.form['username']
        password = request.form['password']

        # TODO: verify username and password by checking hash values in database
        # TODO: when verifying return the id number of the user
        session["LoggedIn"] = True
        from random import randrange
        session["user_id"] = randrange(3)  # To be pulled from DB when Logging in
        session["user_name"] = "NAME"  # To be pulled from DB when Logging in

        return redirect(url_for("homePage"))
    else:
        return render_template("login_page.html")


@app.route("/deposit", methods=["GET", "POST"])
def Deposit():
    gateway = JavaGateway()
    api = gateway.entry_point.getAPI()
    customer = api.getCustomer(session.get("user_id"))
    if request.method == "POST":
        customer.deposit(request.form['Deposit'])
        return redirect(url_for("Deposit"))
    else:
        return render_template("Deposit.html", balance=customer.getBalance())


if __name__ == '__main__':
    print("Initializing app")
    app.run(debug=True)
