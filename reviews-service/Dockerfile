# FROM mcr.microsoft.com/dotnet/sdk:5.0 AS build-env

# COPY WebApi/bin/Debug/net5.0/ App/
# WORKDIR /App

# COPY .env /App

# ENV ASPNETCORE_ENVIRONMENT=Development
# ENV DOTNET_USE_POLLING_FILE_WATCHER=true
# ENV ASPNETCORE_URLS=http://0.0.0.0:5000

# RUN dotnet tool install --global dotnet-ef

# EXPOSE 5000

# ENTRYPOINT ["dotnet", "211150-211064-186499-reviews-service.dll"]

# syntax=docker/dockerfile:1
FROM mcr.microsoft.com/dotnet/sdk:5.0 AS build-env
WORKDIR /app

# Copy csproj and restore as distinct layers
# COPY *.sln ./
# RUN dotnet restore

# Copy everything else and build
COPY ./ ./
RUN dotnet publish -c Release -o out

FROM mcr.microsoft.com/dotnet/sdk:5.0 AS migration
WORKDIR /app
COPY . .
RUN dotnet restore "Migrations/Migrations.csproj"
COPY . .
WORKDIR "Migrations"
RUN dotnet build "Migrations.csproj" -c Release -o /app/migration

# Build runtime image
FROM mcr.microsoft.com/dotnet/aspnet:5.0
WORKDIR /app
COPY --from=build-env /app/out .
COPY --from=migration /app/migration Migrations/
COPY .env .

ENV ASPNETCORE_ENVIRONMENT=Development
ENV DOTNET_USE_POLLING_FILE_WATCHER=true
ENV ASPNETCORE_URLS=http://0.0.0.0:5000

# RUN dotnet tool install --global dotnet-ef

EXPOSE 5000
ENTRYPOINT ["dotnet", "211150-211064-186499-reviews-service.dll"]
