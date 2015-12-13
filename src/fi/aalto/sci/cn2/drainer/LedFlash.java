package fi.aalto.sci.cn2.drainer;

import android.hardware.Camera;

public class LedFlash {
    private Camera camera = null;
    private Camera.Parameters cameraParameters;

    private String previousFlashMode = null;

    public synchronized void open() {
        camera = Camera.open();
        if (camera != null) {
            cameraParameters = camera.getParameters();
            previousFlashMode = cameraParameters.getFlashMode();
        }
    }

    public synchronized void close() {
        if (camera != null) {
            cameraParameters.setFlashMode(previousFlashMode);
            camera.setParameters(cameraParameters);
            camera.release();
            camera = null;
        }
    }

    public synchronized void on() {
        if (camera != null) {
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(cameraParameters);
            camera.startPreview();
        }
    }

    public synchronized void off() {
        if (camera != null) {
            camera.stopPreview();
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(cameraParameters);
        }
    }

}