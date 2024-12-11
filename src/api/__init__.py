"""Flask application factory."""
from flask import Flask
from src.api.routes import api
from src.api.error_handlers import register_error_handlers

def create_app():
    """Create and configure the Flask application."""
    app = Flask(__name__)
    
    # Register blueprints
    app.register_blueprint(api)  # Remove url_prefix to match current routes
    
    # Register error handlers
    register_error_handlers(app)
    
    return app