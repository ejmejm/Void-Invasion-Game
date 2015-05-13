package Math;

import Entities.Camera;

public class Matrix4f{
	
	// -- Reference a value from a matrix as: [row + column * 4] -- \\

	public float[] elements = new float[4 * 4];

	public Matrix4f(){
		for (int i = 0; i < 4 * 4; i++){
			elements[i] = 0.0f;
		}
	}

	public Matrix4f(float diagonal){
		for (int i = 0; i < 4 * 4; i++){
			elements[i] = 0.0f;
		}

		elements[0 + 0 * 4] = 1.0f;
		elements[1 + 1 * 4] = 1.0f;
		elements[2 + 2 * 4] = 1.0f;
		elements[3 + 3 * 4] = 1.0f;
	}

	public static Matrix4f identity(){
		return new Matrix4f(1.0f);
	}

	public Matrix4f multiply(Matrix4f other){
		for (int y = 0; y < 4; y++){
			for (int x = 0; x < 4; x++){
				float sum = 0;
				for (int e = 0; e < 4; e++){
					sum += elements[x + e * 4] * other.elements[e + y * 4];
				}
				elements[x + y * 4] = sum;
			}
		}
		return this;
	}

	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far){
		Matrix4f result = new Matrix4f(1.0f);

		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		result.elements[2 + 2 * 4] = 2.0f / (near - far);

		result.elements[0 + 3 * 4] = 2.0f / (left + right) / (left - right);
		result.elements[1 + 3 * 4] = 2.0f / (bottom - top) / (bottom - top);
		result.elements[2 + 3 * 4] = 2.0f / (far - near) / (far - near);

		return result;
	}

	public static Matrix4f projection(float fov, float aspectRatio, float near, float far){
		Matrix4f result = new Matrix4f(1.0f);

		float q = (float) (1.0f / Math.tan(Math.toRadians(0.5f * fov)));
		float a = q / aspectRatio;
		float b = (near + far) / (near - far);
		float c = (2.0f * near * far) / (near - far);

		result.elements[0 + 0 * 4] = a;
		result.elements[1 + 1 * 4] = q;
		result.elements[2 + 2 * 4] = b;
		result.elements[3 + 2 * 4] = -1.0f;
		result.elements[2 + 3 * 4] = c;
		return result;
	}

	public static Matrix4f translation(Vector3f translation){
		Matrix4f result = new Matrix4f(1.0f);

		result.elements[0 + 3 * 4] = translation.x;
		result.elements[1 + 3 * 4] = translation.y;
		result.elements[2 + 3 * 4] = translation.z;

		return result;
	}

	public void rotate(float angle, Vector3f axis){
		Matrix4f dest = this;
		Matrix4f src = this;
		
		float r = (float) Math.toRadians(angle);
		float c = (float) Math.cos(r);
		float s = (float) Math.sin(r);
		float omc = 1.0f - c;

		float xy = axis.x*axis.y;
		float yz = axis.y*axis.z;
		float xz = axis.x*axis.z;
		float xs = axis.x*s;
		float ys = axis.y*s;
		float zs = axis.z*s;

		float f00 = axis.x*axis.x*omc+c;
		float f01 = xy*omc+zs;
		float f02 = xz*omc-ys;
		// n[3] not used
		float f10 = xy*omc-zs;
		float f11 = axis.y*axis.y*omc+c;
		float f12 = yz*omc+xs;
		// n[7] not used
		float f20 = xz*omc+ys;
		float f21 = yz*omc-xs;
		float f22 = axis.z*axis.z*omc+c;
		
		float t00 = src.elements[0 + 0 * 4] * f00 + src.elements[0 + 1 * 4] * f01 + src.elements[0 + 2 * 4] * f02;
		float t01 = src.elements[1 + 0 * 4] * f00 + src.elements[1 + 1 * 4] * f01 + src.elements[1 + 2 * 4] * f02;
		float t02 = src.elements[2 + 0 * 4] * f00 + src.elements[2 + 1 * 4] * f01 + src.elements[2 + 2 * 4] * f02;
		float t03 = src.elements[3 + 0 * 4] * f00 + src.elements[3 + 1 * 4] * f01 + src.elements[3 + 2 * 4] * f02;
		float t10 = src.elements[0 + 0 * 4] * f10 + src.elements[0 + 1 * 4] * f11 + src.elements[0 + 2 * 4] * f12;
		float t11 = src.elements[1 + 0 * 4] * f10 + src.elements[1 + 1 * 4] * f11 + src.elements[1 + 2 * 4] * f12;
		float t12 = src.elements[2 + 0 * 4] * f10 + src.elements[2 + 1 * 4] * f11 + src.elements[2 + 2 * 4] * f12;
		float t13 = src.elements[3 + 0 * 4] * f10 + src.elements[3 + 1 * 4] * f11 + src.elements[3 + 2 * 4] * f12;
		dest.elements[0 + 2 * 4] = src.elements[0 + 0 * 4] * f20 + src.elements[0 + 1 * 4] * f21 + src.elements[0 + 2 * 4] * f22;
		dest.elements[1 + 2 * 4] = src.elements[1 + 0 * 4] * f20 + src.elements[1 + 1 * 4] * f21 + src.elements[1 + 2 * 4] * f22;
		dest.elements[2 + 2 * 4] = src.elements[2 + 0 * 4] * f20 + src.elements[2 + 1 * 4] * f21 + src.elements[2 + 2 * 4] * f22;
		dest.elements[3 + 2 * 4] = src.elements[3 + 0 * 4] * f20 + src.elements[3 + 1 * 4] * f21 + src.elements[3 + 2 * 4] * f22;
		dest.elements[0 + 0 * 4] = t00;
		dest.elements[1 + 0 * 4] = t01;
		dest.elements[2 + 0 * 4] = t02;
		dest.elements[3 + 0 * 4] = t03;
		dest.elements[0 + 1 * 4] = t10;
		dest.elements[1 + 1 * 4] = t11;
		dest.elements[2 + 1 * 4] = t12;
		dest.elements[3 + 1 * 4] = t13;
		
		//return dest;
		
		/*
		result.elements[0 + 0 * 4] = x * omc + c;
		result.elements[1 + 0 * 4] = y * x * omc + z * s;
		result.elements[2 + 0 * 4] = x * z * omc - y * s;

		result.elements[0 + 1 * 4] = x * y * omc - z * s;
		result.elements[1 + 1 * 4] = y * omc + c;
		result.elements[2 + 1 * 4] = y * z * omc + x * s;

		result.elements[0 + 2 * 4] = x * z * omc + y * s;
		result.elements[1 + 2 * 4] = y * z * omc - x * s;
		result.elements[2 + 2 * 4] = z * omc + c;
		
		return result;
		 */
	}
	
	public static Matrix4f view(Camera camera){
		Matrix4f result = new Matrix4f(1.0f);
		result.rotate(camera.getPitch(), new Vector3f(1, 0, 0));
		result.rotate(camera.getYaw(), new Vector3f(0, 1, 0));
		result.rotate(camera.getRoll(), new Vector3f(0, 0, 1));
		/*multiply(Matrix4f.rotation((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0)))
			.multiply(Matrix4f.rotation((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0)))
			.multiply(Matrix4f.rotation((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1)));*/
		Vector3f camPos = camera.getPosition();
		result.multiply(Matrix4f.translation(new Vector3f(camPos.x * -1, camPos.y * -1, camPos.z * -1)));
		return result;
	}
	
	public static Matrix4f genMatrix(Vector3f position, Vector3f rotation, Vector3f scale){
		Matrix4f result = Matrix4f.identity()
				.multiply(Matrix4f.translation(position))
				/*.multiply(Matrix4f.rotation(rotation.x, new Vector3f(1, 1, 1)))*/
				.multiply(Matrix4f.scale(new Vector3f(scale.x, scale.y, scale.z)));
		result.rotate(rotation.x, new Vector3f(0, 1 ,0)); //Correct?
		result.rotate(rotation.y, new Vector3f(1, 0 ,0)); //Correct?
		result.rotate(rotation.z, new Vector3f(0, 0 ,1));
		return result;
	}

	public static Matrix4f scale(Vector3f scale){
		Matrix4f result = new Matrix4f(1.0f);

		result.elements[0 + 0 * 4] = scale.x;
		result.elements[1 + 1 * 4] = scale.y;
		result.elements[2 + 2 * 4] = scale.z;

		return result;
	}
}
