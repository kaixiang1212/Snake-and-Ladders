from flask import Flask
from flask_login import LoginManager
from client import *

host = "localhost"
port = 8000

app = Flask(__name__)
app.secret_key = 'snek and ladder'

# Login manager stuff
login_manager = LoginManager()
login_manager.init_app(app)
login_manager.login_view = 'login'

players = []
_id = 0


def add_player(name):
    global _id
    player = client(_id, name)
    players.append(player)
    _id += 1
    player.send("\n")
    return player


def get_user_by_id(player_id):
    for player in players:
        if player.get_id() == player_id:
            return player
    return None


@login_manager.user_loader
def load_user(player_id):
    return get_user_by_id(player_id)
