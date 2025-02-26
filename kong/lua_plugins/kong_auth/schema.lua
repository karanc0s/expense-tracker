local typedefs = require "kong.db.schema.typedefs"

return {
    name = "kong_auth",
    fields = {
        { consumer = typedefs.no_consumer },
        { protocols = typedefs.protocols_http },
        {
            config = {
                type = "record",
                fields = {
                    {
                        auth_service_url = {
                            type = "string",
                            require = true,
                            default = "http://auth-service:7001/auth/v1/health"
                        }
                    }
                }
            }
        }
    }
}