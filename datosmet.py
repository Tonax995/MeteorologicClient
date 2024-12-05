import socket
import requests
import json
import time

# Configuración del cliente Java
CLIENT_IP = "127.0.0.1"  # Cambia a la IP del cliente si no es localhost
CLIENT_PORT = 9876

# Configuración de la API de Open-Meteo
LATITUDE = 20.6597  # Latitud de Guadalajara, México
LONGITUDE = -103.3496  # Longitud de Guadalajara, México
API_URL = f"https://api.open-meteo.com/v1/forecast?latitude={LATITUDE}&longitude={LONGITUDE}&current_weather=true"

# Crear el socket UDP
server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

def obtener_datos_reales():
    """Obtiene datos meteorológicos reales desde la API Open-Meteo."""
    try:
        response = requests.get(API_URL)
        response.raise_for_status()
        data = response.json()

        # Formatear los datos en el formato esperado por el cliente Java
        current_weather = data["current_weather"]
        datos_formateados = {
            "temperatura": current_weather["temperature"],
            "humedad": 50.0,  # Open-Meteo no proporciona humedad; simulamos un valor
            "velocidadViento": current_weather["windspeed"],
            "direccionViento": current_weather["winddirection"],
            "presionAtmosferica": 1013.0,  # Simulación: Open-Meteo no proporciona presión
            "puntoRocio": current_weather["temperature"] - 5,  # Aproximación
            "indiceUV": 5.0,  # Simulación: Open-Meteo no proporciona UV
            "cantidadPrecipitacion": 0.0,  # Open-Meteo no proporciona precipitación en esta llamada
            "visibilidad": 10.0,  # Simulación
            "altitudNubosidad": 2000.0,  # Simulación
            "condicionActual": current_weather["weathercode"]
        }
        return json.dumps(datos_formateados)
    except requests.RequestException as e:
        print(f"Error al obtener datos de la API: {e}")
        return None

def enviar_datos():
    """Envía datos meteorológicos al cliente Java."""
    while True:
        datos = obtener_datos_reales()
        if datos:
            print(f"Enviando datos: {datos}")
            server_socket.sendto(datos.encode(), (CLIENT_IP, CLIENT_PORT))
        time.sleep(60)  # Enviar datos cada 60 segundos

if __name__ == "__main__":
    try:
        print(f"Servidor enviando datos meteorológicos reales a {CLIENT_IP}:{CLIENT_PORT}")
        enviar_datos()
    except KeyboardInterrupt:
        print("\nServidor detenido.")
        server_socket.close()
