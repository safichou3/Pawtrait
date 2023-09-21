import { IUser } from '@/shared/model/user.model';

export interface IApplicationUser {
  id?: string;
  profilePicture?: string | null;
  bio?: string | null;
  status?: string | null;
  createdAt?: Date | null;
  updatedAt?: Date | null;
  deletedAt?: Date | null;
  internalUser?: IUser | null;
}

export class ApplicationUser implements IApplicationUser {
  constructor(
    public id?: string,
    public profilePicture?: string | null,
    public bio?: string | null,
    public status?: string | null,
    public createdAt?: Date | null,
    public updatedAt?: Date | null,
    public deletedAt?: Date | null,
    public internalUser?: IUser | null
  ) {}
}
