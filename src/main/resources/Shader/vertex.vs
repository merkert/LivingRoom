#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertexNormal;

out vec2 outTexCoord;
out vec3 mvVertexNormal;
out vec3 mvVertexPos;
out mat4 outModelViewMatrix;

uniform mat4 modelViewMatrix;
uniform mat4 projectionsMatrix;

void main()
{
   vec4 mvPos = modelViewMatrix *vec4(position,1.0);
   gl_Position = projectionsMatrix *mvPos;
   outTexCoord = texCoord;
   mvVertexNormal = normalize(modelViewMatrix*vec4(vertexNormal,0.0)).xyz;
   mvVertexPos = mvPos.xyz;
   outModelViewMatrix = modelViewMatrix;
}