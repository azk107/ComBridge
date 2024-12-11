"""Flask application factory."""
from flask import Flask
from src.api.routes import api
from src.api.error_handlers import register_error_handlers
from src.config import API_PREFIX

def create_app():
    """Create and configure the Flask application."""
    app = Flask(__name__)
    
    # Register blueprints
    app.register_blueprint(api, url_prefix=API_PREFIX)
    
    # Register error handlers
    register_error_handlers(app)
    
    return app