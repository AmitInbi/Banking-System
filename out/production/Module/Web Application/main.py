from flask import Flask, render_template, request, redirect, url_for, session
from os import urandom
import json

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

        # Temporary: get random user by id
        from random import randrange
        session["user_id"] = randrange(3)  # To be pulled from DB when Logging in

        # Create Customer Object
        gateway = JavaGateway()
        api = gateway.entry_point.getAPI()
        customer = api.getCustomer(str(session.get("user_id")))
        session["customer"] = json.loads(str(customer.toJSON()))
        print(session["customer"])
        # session["customer"] = json.dumps(customer, default=lambda o: o._asdict, sort_keys=True, indent=4)
        # session["customer"] = json.dumps(customer.__dict__)

        # # TODO: get user first and last name from DB (need to create getFirstName, getLastName JAVA methods)
        # # session["user_name"] = session["customer"].getFirstName + session["customer"].getLastName
        session["user_name"] = "NAME"  # Temp

        return redirect(url_for("homePage"))
    else:
        return render_template("login_page.html")


@app.route("/deposit", methods=["GET", "POST"])
def Deposit():
    if request.method == "POST":
        session.get("customer").deposit(int(request.form['Deposit']))
        return redirect(url_for("Deposit"))
    else:
        return render_template("Deposit.html", balance=session.get("customer").getBalance())


if __name__ == '__main__':
    print("Initializing app")
    app.run(debug=True)
