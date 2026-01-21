import { jwtDecode } from "jwt-decode";

type JwtPayload = {
  sub: string;
  name: string;
  roles: { roleId: number; description: string }[];
  exp: number;
};

export function getUserFromToken(token: string) {
  return jwtDecode<JwtPayload>(token);
}
