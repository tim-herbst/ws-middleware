import {MonumentType} from "./monument-type";

export interface Monument {
  id: string;
  objectNumber: number;
  type: MonumentType;
  name: string;
  description: string;
  address: string;
  county: string;
  community: string;
  photoUrl: string;
  justifications: string;
  scopeOfProtections: string;
}
